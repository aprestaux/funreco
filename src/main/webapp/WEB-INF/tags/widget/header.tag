<%@ tag language="java" pageEncoding="UTF-8"%>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">

		<a class="brand" href="/" style="margin-left: 20px">Funreco</a>
		<form class="form-horizontal" action="/searchProfile"
			style="float: left">
			<input type="text" class="input-large" placeholder="Email" name="email"> <input
				type="text" class="input-large" placeholder="Facebook ID" name="facebookId" >
			<button type="submit" class="btn">
				<i class="icon-search"></i> Search profile
			</button>
		</form>

		<form class="form-inline" actions="/latestActions">
			<input type="text" class="input-large" style="margin-left: 50px"
				"placeholder="Facebook ID" name="facebookId">
			<button type="submit" class="btn">
				<i class="icon-search"></i> Search latest actions
			</button>
		</form>

	</div>
</div>

