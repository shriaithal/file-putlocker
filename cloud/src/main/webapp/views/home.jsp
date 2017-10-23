<!DOCTYPE html>
<html>
<head>
<!-- <link rel="stylesheet" type="text/css" href="/resources/styles/bootstrap.min.css" /> -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/resources/styles/home.css" />
</head>
<body>
	<script type="text/javascript" src="/resources/script/angular.min.js"></script>
	<script type="text/javascript" src="/resources/script/ng-file-upload.js"></script>
	<script type="text/javascript" src="/resources/script/bootstrap.3.3.5.min.js"></script>
	<div ng-app="fileStoreApp" ng-controller="homeCtrl">

		<nav class="navbar" role="navigation" style="background-color: rgba(44, 116, 193, 0.76);">
			<a class="navbar-brand" style="/* margin-left: -588px; */color: white;">File Putlocker</a> <a class="navbar-right" style="margin-right: auto;color: white;">Welcome {{customer.lastName}}, {{customer.firstName}}</a>
			<div class="navbar-header">
				<a onclick="logout()" href="#!login" style="/* margin-right: -320px; */color: white;"><span class="glyphicon glyphicon-log-out"></span>Logout</a>
			</div>
		</nav>

		<div class="container-fluid">
		<div class="alert alert-success" ng-show="showSuccessAlert">
			<button type="button" class="close" data-ng-click="switchBool('showSuccessAlert')">×</button>
			<strong>Done!</strong> {{successTextAlert}}
		</div>

		<div class="alert alert-danger" ng-show="showErrorAlert">
			<button type="button" class="close" data-ng-click="switchBool('showErrorAlert')">×</button>
			<strong>Error!</strong> {{errorTextAlert}}
		</div>

		<div>

			<form name="f" id="upload-form" method="POST" enctype="multipart/form-data" onsubmit="uploadPartnerImg(); return false;" class="form-inline" style="padding-top: 30px; padding-bottom: 30px;">

				<div>
					<input type='file' data-ng-model="custFile" onchange="angular.element(this).scope().customerFileOnChange(this)" data-classButton="btn btn-link" ngf-select required data-input="false" name="file"
						class="form-control mb-2 mr-sm-2 mb-sm-0 no-border" />

					<div class="form-control mb-2 mr-sm-2 mb-sm-0 no-border"> Description : <input type="text" name="description" data-ng-model="description" id="description" />
					</div>
				</div>
				<div class="form-control mb-2 mr-sm-2 mb-sm-0 no-border">
					<a href="javascript:void(0)" data-ng-click="uploadCustomerFile(custFile)" class="btn btn-outline-primary">Upload File</a>
				</div>
			</form>
		</div>

		<div ng-show="isDataPresent">
			<div class="table-responsive">
				<table id="mytable" class="table table-bordred table-striped">
					<thead>
						<th>File Name</th>
						<th>File Description</th>
						<th>File Size</th>
						<th>File Created Time</th>
						<th>File Updated Time</th>
						<th>Download</th>
						<th>Delete</th>
						<th>Edit</th>
					</thead>

					<tbody>

						<tr data-ng-repeat="customerFile in customerFiles">
							<td>{{customerFile.name}}</td>
							<td>{{customerFile.description}}</td>
							<td>{{customerFile.fileSize}}</td>
							<td>{{customerFile.createdTime | date:'yyyy-MM-dd HH:mm:ss'}}</td>
							<td>{{customerFile.updatedTime | date:'yyyy-MM-dd HH:mm:ss'}}</td>
							<td><a href="http://dnv45yqu78499.cloudfront.net/{{customerFile.path}}" target="_blank"><span class="glyphicon glyphicon-download-alt"></span></a></td>
							<td><p data-placement="top" data-toggle="tooltip" title="Delete">
									<button class="btn-danger btn-xs" name="deleteFile" id="deleteFile" value="Delete File" ng-click="deleteFile(customerFile)">
										<span class="glyphicon glyphicon-trash"></span>
									</button>
								</p></td>
							<td><a data-toggle="tooltip" data-placement="top" title="To update file, download -> edit contents -> upload"><span class="glyphicon glyphicon-pencil"></span></a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div ng-show="!isDataPresent">
			<p style="text-align: center; font-size: larger;">No Files uploaded</p>
		</div>
		</div>
	</div>
</body>
</html>