<%@ tag language="java" pageEncoding="UTF-8"%>

<div class="navbar navbar-inverse">
	<div class="navbar-inner">

		<a class="brand" href="/" style="margin-left: 20px">Funreco</a> <a
			href="/logout"
			style="float: right; color: gray; text-decoration: none">Logout <i
			class="icon-white icon-off"></i></a>
		<form class="navbar-form" action="/searchProfile">
			<input type="text" name="email" class="input-large"
				placeholder="Email..."> <input type="text" name="facebookId"
				class="input-large" placeholder="Facebook ID...">
			<button type="submit" class="btn">
				<i class="icon-search"></i>Search profile
			</button>
		</form>





	</div>
</div>

