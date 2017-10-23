//var homeApp = angular.module('homeApp', [ 'ngFileUpload' ]);
app.controller('homeCtrl', [ 'Upload', '$scope', '$http', '$location',
		function(Upload, $scope, $http, $location) {
			//$scope.username = $routeParams.username;
			var init = function() {
				$scope.firstName = "Anu";
			};

			$scope.imageUpload = function(element) {

				var reader = new FileReader();
				reader.onload = $scope.imageIsLoaded;
				reader.readAsDataURL(element.files[0]);
			}

			$scope.uploadPartnerImg = function(file) {

				file.upload = Upload.upload({
					url : "/uploadFile",
					type : "POST",
					data : {
						file : file,
						fileName : file.name,
						description : $scope.description
					}
				});

				file.upload.then(function(response) {

					alert('yes');

				}, function(response) {

					alert('no')
				});
			};
			init();
		} ]);