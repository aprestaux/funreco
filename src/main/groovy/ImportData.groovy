
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.6')
import groovyx.net.http.RESTClient

import groovy.json.JsonSlurper

def cli = new CliBuilder(usage: 'groovy ImportData.groovy')
cli.a(longOpt:'all', 'Import all')
cli._(longOpt:'profiles', 'Import profiles')
cli._(longOpt:'friends', 'Import friends')
cli._(longOpt:'actions', 'Import actions')
cli._(longOpt:'dataDir', args: 1, 'Directory containing files')

options = cli.parse(args)

if (! (options.a || options.profiles || options.friends || options.actions)) {
    cli.usage()
    System.exit(-1)
}

println 'connecting on http://localhost:8080'
rest = new RESTClient('http://localhost:8080')

if (options.a || options.profiles) {
    processFile('profiles.export') { json, count ->
        rest.put(path: "/api/profiles/${json.facebookId}",
                requestContentType: 'application/json; charset=UTF-8',body: [
                        email: [json.email],
                        name: [json.name]
                ])

        printStatus "done ${count} profiles"
    }
}

if (options.a || options.friends) {
    processFile('friends.export') { json, count ->
        def friends = json.friends.collect { [id: it.facebookId] }

        rest.put(path: "/api/profiles/${json.facebookId}/friends",
                requestContentType: 'application/json; charset=UTF-8',body: friends)


        printStatus "done friends for ${count} profiles"
    }
}

if (options.a || options.actions) {
    processFile('actions.export') { json, count ->
        rest.post(path: "/api/profiles/${json.facebookId}/actions",
                requestContentType: 'application/json; charset=UTF-8',body: [
                    date: json.date,
                    object: [
                        id: json.object.id,
                        attributes: json.object.properties
                    ]
                ])

        printStatus "done actions for ${count} profiles"
    }
}

def processFile(String filename, Closure closure) {
    try {
        def parent = options.dataDir ? new File(options.dataDir) : new File('.')

        def file = new File(parent, filename)

        println "reading ${filename} (${lineCount(file)} lines)"

        def count = 1;

        file.eachLine('UTF-8') { line ->
            try {
                closure.call(new JsonSlurper().parseText(line), count)

                System.out.flush()

                count++;
            } catch (e) {
                println "Failed to process line ${line}"

                throw e
            }
        }
    } catch (Exception e) {
        e.printStackTrace(System.out)

        System.exit(-1)
    }

    println ""
    println "done with ${filename}"
}

def printStatus(def text) {
    print "\033[K\r"
    print text
}

def lineCount(File file) {
    def count = 0;
    file.eachLine { count++ }
    count
}