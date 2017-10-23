<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/styles/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/resources/styles/app.css" />
</head>
<body>
	<script type="text/javascript" src="/resources/script/angular.min.js"></script>
	<script type="text/javascript" src="/resources/script/angular-route.min.js"></script>
	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<div ng-app="fileStoreApp" ng-controller="fileStoreCtrl">
	
	<div class="alert alert-danger" ng-show="showErrorAlert">
        <button type="button" class="close" data-ng-click="switchBool('showErrorAlert')">×</button> <strong>Error!</strong> {{errorTextAlert}}</div>
        
	<div class="container">
	<h1 class="welcome text-center">Welcome</h1>
        <div class="card card-container">
    <h2 class='login_title text-center title-login'>Login</h2>
    <hr>
    	<form class="form-signin" name="login" ng-submit="loginForm()">
                
           <span id="reauth-email" class="reauth-email"></span>
           <p class="input_title">Email</p>
           <input type='text' name='userName' ng-model='form.userName' id='userName' class="login_box"  required autofocus>
           
           <p class="input_title">Password</p>
          	<input type='password' name='password' ng-model='form.password' id='password' class="login_box"  required>
                
          <div class="row">
      		<div class="col-xs-4 buttons-login">
      			<input name="submit" type="submit" id="submitForm" value="Submit" class="btn btn-primary btn-sm"/>
      		</div>
      	
      		<div class="col-xs-4 buttons-login">
      			<input name="cancel" type="reset" id="resetForm" value="Cancel" class="btn btn-primary btn-sm"/>
      		</div>
      	</div>
      	<div class="row">
      	<a href="#!signUp" style="font-size: small; padding-left: 15px;">New User? Sign up here!</a>
      	</div>
     </form><!-- /form -->
  </div><!-- /card-container -->
</div>
	</div>
</body>
</html>