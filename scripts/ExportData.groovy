@Grab(group = 'com.gmongo', module = 'gmongo', version = '1.0') import com.gmongo.GMongo
import groovy.json.JsonBuilder
import groovy.json.JsonOutput

def cli = new CliBuilder(usage: 'groovy ExportData.groovy')
cli.h(longOpt:'host', args: 1, 'Address (host:port) of mongodb database')
cli.a(longOpt:'all', 'Export all')
cli._(longOpt:'profiles', 'Export profiles')
cli._(longOpt:'friends', 'Export friends')
cli._(longOpt:'actions', 'Export actions')

def options = cli.parse(args)

if (!options.h) {
    cli.usage()
    System.exit(-1)
}

if (! (options.a || options.profiles || options.friends || options.actions)) {
    cli.usage()
    System.exit(-1)
}

db = new GMongo(options.host).getDB('community')

// export profiles
if (options.a || options.profiles) {
    exportCollection('facebook.profiles', 'profiles.export') { row, json ->
        json {
            facebookId row.facebookId
            email row.email
            name row.name
        }
    }
}

// export friends
if (options.a || options.friends) {
    exportCollection('facebook.friends', 'friends.export') { row, json ->
        json {
            facebookId row.profile.facebookId
            friends row.friends
        }
    }
}

// export actions
if (options.a || options.actions) {
    exportCollection('facebook.opengraph.actions', 'actions.export') { row, json ->
        json {
            facebookId row.profile.facebookId
            date row.date.time
            object row.object
        }
    }
}

def exportCollection(String name, String filename, Closure closure) {
    println "Exporting collection ${name} to ${filename}"

    def file = prepareFile(filename)

    def count = 1;

    db.getCollection(name).find().each { row ->
        def json = new JsonBuilder()

        closure.call(row, json)

        file << JsonOutput.prettyPrint(json.toString()).replaceAll('\n', ' ') << '\n'

        print "\033[K\rexported ${count} rows"
        count++
    }

    println "\ndone"
}

File prepareFile(String filename) {
    File file = new File(filename)
    file.text = ''
    file
}