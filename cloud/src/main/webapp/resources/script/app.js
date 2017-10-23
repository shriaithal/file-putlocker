var app = angular.module('fileStoreApp', [ 'ngRoute', 'ngFileUpload' ]);

app.service('fileStoreService', function() {
	var isLoggedIn = false;
	
	var setLoggedIn= function(flag) {
		isLoggedIn = flag;
	};
	
	var getLoggedIn=function() {
		return isLoggedIn;
	};
	
	return {
		setLoggedIn: setLoggedIn,
		getLoggedIn: getLoggedIn
	  };
});
app.config(function($routeProvider) {

	$routeProvider.when('/', {
		templateUrl : 'views/login.jsp',
		controller : 'fileStoreCtrl'
	}).when('/home/:username', {
		templateUrl : 'views/home.jsp',
		controller : 'homeCtrl'
	}).when('/signUp', {
		templateUrl : 'views/signUp.jsp',
		controller : 'fileStoreCtrl'
	}).otherwise({
		redirectTo : "/"
	});
});

app.controller("fileStoreCtrl", function($scope, $http, $location, fileStoreService) {
	var init = function() {
		fileStoreService.setLoggedIn(false);
	};
	$scope.loginForm = function() {
		var req = {
			method : 'POST',
			url : "/login/action",
			data : angular.toJson($scope.form),
			headers : {
				'Content-Type' : "application/json"
			}
		};
		$http(req).then(function(response) {
			var username = response.data.emailId;
			fileStoreService.setLoggedIn(true);
			$location.path('/home/' + username);
		}, function(error) {
			$scope.showErrorAlert = true;
			$scope.errorTextAlert = "Username/password is incorrect";
		});
	};
	
	$scope.switchBool = function (value) {
        $scope[value] = !$scope[value];
    };
    
    $scope.signUp = function() {
    	if($scope.form.emailId != $scope.form.reenteremail) {
    		$scope.showErrorAlert = true;
			$scope.errorTextAlert = "Email Ids do not match!";
			return;
    	}
    	var req = {
    			method : 'POST',
    			url : "/signUp",
    			data : angular.toJson($scope.form),
    			headers : {
    				'Content-Type' : "application/json"
    			}
    		};
    		$http(req).then(function(response) {
    			$scope.showSuccessAlert = true;
				$scope.successTextAlert = "Successfully signed up! Login to continue";
				$scope.form = null;
    		}, function(error) {
    			$scope.showErrorAlert = true;
    			$scope.errorTextAlert = error.data.message;
    		});
    };
    
   init();

});

app.controller("homeCtrl", [
		'Upload',
		'$scope',
		'$http',
		'$location',
		'$routeParams',
		'$q',
		'fileStoreService',
		function(Upload, $scope, $http, $location, $routeParams, $q, fileStoreService) {
			$scope.username = $routeParams.username;
			$scope.isDataPresent = false;

			var init = function() {
				if(!fileStoreService.getLoggedIn()) {
					$location.path('/');
					return;
				}
				displayAllCustomerFiles();
			};

			var displayAllCustomerFiles = function() {

				getCustomerData().then(function(response) {

					getCustomerFiles().then(function(response) {
						
					}, function(error) {
						$scope.isDataPresent = false;
					});
				}, function(error) {
					// showNotification( 'error' );
				});
			};

			getCustomerData = function() {
				var deferred = $q.defer();
				var req = {
					params : {
						"userName" : $scope.username
					},
					method : 'GET',
					url : "/customer",
					headers : {
						'Content-Type' : "application/json"
					}
				}
				$http(req).then(function(response) {
					$scope.customer = response.data;
					deferred.resolve($scope.customer);
				}, function(error) {
					deferred.reject(error);
				});

				return deferred.promise;
			};

			getCustomerFiles = function() {
				var deferred = $q.defer();
				var req = {
					method : 'GET',
					url : "/allCustomerFiles",
					params : {
						"userId" : $scope.customer.id
					},
					headers : {
						'Content-Type' : "application/json"
					}
				}
				$http(req).then(function(response) {
					$scope.customerFiles = response.data.customerFileList;
					if ($scope.customerFiles.length != 0) {
						 $scope.isDataPresent = true;
					}
					deferred.resolve($scope.customerFiles);
				}, function(error) {
					deferred.reject(error);
				});
				return deferred.promise;
			};

			$scope.customerFileOnChange = function(element) {

				var reader = new FileReader();
				reader.readAsDataURL(element.files[0]);
			};

			$scope.uploadCustomerFile = function(file) {
				
				if(file == null) {
					$scope.showErrorAlert = true;
					$scope.errorTextAlert = "No File to upload!";
					return;
				}
				
				if(file.size > 10485760) {
					$scope.showErrorAlert = true;
					$scope.errorTextAlert = "Failed to upload! File Size is more than 10MB!";
					return;
				}
				file.upload = Upload.upload({
					url : "/uploadFile",
					type : "POST",
					data : {
						file : file,
						fileName : file.name,
						description : $scope.description,
						userName : $scope.username

					}
				});

				file.upload.then(function(response) {
					var spliceIndex = -1;
					for (var i = 0; i < $scope.customerFiles.length; i++) {
						if ($scope.customerFiles[i].id == response.data.id) {
							spliceIndex = i;
							break;
						}
					}
					if (spliceIndex != -1) {
						$scope.customerFiles.splice(spliceIndex, 1);
					}
					$scope.customerFiles.push(response.data);
					$scope.description = "";
					$scope.custFile = null;

					$scope.showSuccessAlert = true;
					$scope.successTextAlert = "Successfully uploaded the file";
					$scope.isDataPresent = true;

				}, function(response) {
					$scope.showErrorAlert = true;
					$scope.errorTextAlert = "File upload failed! Please try again after sometime!";
				});
			};

			$scope.deleteFile = function(customerFile) {
				var req = {
					method : 'POST',
					url : "/deleteFile",
					data : {
						id : customerFile.id,
						fileName : customerFile.name,
						userName : $scope.customer.emailId
					},
					headers : {
						'Content-Type' : "application/json"
					}
				};
				$http(req).then(
						function(response) {
							var index = $scope.customerFiles.indexOf(customerFile);
							$scope.customerFiles.splice(index, 1);
							
							if($scope.customerFiles == []) {
								$scope.isDataPresent = false;
							}
							$scope.showSuccessAlert = true;
							$scope.successTextAlert = "File deleted successfully!";

						}, function(error) {
							$scope.showErrorAlert = true;
							$scope.errorTextAlert = "File delete failed! Please try again after sometime!";
						});
			};
			
			$scope.logout = function() {
				fileStoreService.setLoggedIn(false);
			};
			
			$scope.switchBool = function (value) {
		        $scope[value] = !$scope[value];
		    };

			init();
		} ]);
