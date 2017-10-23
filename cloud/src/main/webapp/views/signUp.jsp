<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/styles/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/resources/styles/signUp.css" />
</head>
<body>
	<script type="text/javascript" src="/resources/script/angular.min.js"></script>
	<script type="text/javascript" src="/resources/script/angular-route.min.js"></script>
	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<div ng-app="fileStoreApp" ng-controller="fileStoreCtrl">
	
	<nav class="navbar navbar-default" role="navigation" style="background-color: rgba(44, 116, 193, 0.01);">
			<a class="navbar-brand navbar-right" style="color: white;"></a>
			<div class="navbar-header">
				<a class="navbar-brand navbar-right" onclick="#!login" href="#!login" style="font-size: medium;">Login</a>
			</div>
		</nav>
		
	<div class="alert alert-success" ng-show="showSuccessAlert">
			<button type="button" class="close" data-ng-click="switchBool('showSuccessAlert')">×</button>
			<strong>Done!</strong> {{successTextAlert}}
		</div>

		<div class="alert alert-danger" ng-show="showErrorAlert">
			<button type="button" class="close" data-ng-click="switchBool('showErrorAlert')">×</button>
			<strong>Error!</strong> {{errorTextAlert}}
		</div>
		
		<div class="container card card-container">
		<legend><a href="http://www.jquery2dotnet.com"><i class="glyphicon glyphicon-globe" style="text-align: center;"></i></a> Sign up!</legend>
    <div class="row">
        <div>
            <form name="signUpForm" ng-submit="signUp()" class="form" role="form">
            <div class="row">
                <div class="col-xs-6 col-md-6">
                    <input class="form-control" ng-model="form.firstname" name="firstname" placeholder="First Name" type="text"
                        required autofocus />
                </div>
                <div class="col-xs-6 col-md-6">
                    <input class="form-control" ng-model="form.lastname" name="lastname" placeholder="Last Name" type="text" required />
                </div>
            </div>
            <input class="form-control" name="emailId" ng-model="form.emailId" placeholder="Your Email" type="email" />
            <input class="form-control" name="reenteremail" ng-model="form.reenteremail" placeholder="Re-enter Email" type="email" />
            <input class="form-control" name="password" ng-model="form.password" placeholder="New Password" type="password" />
            <br />
            <button class="btn btn-lg btn-primary btn-block" type="submit">
                Sign up</button>
            </form>
        </div>
    </div>
</div>
</div>
</body>
</html>