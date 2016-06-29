ctrls.controller("semesterCtrl",['$scope', '$rootScope', 'Semester', 'Pageable', 'GetSemester', '$stateParams', '$filter',
	function($scope, $rootScope, Semester, Pageable, GetSemester, $stateParams, $filter){
		$scope.selectSchool = {
			isEdit: true,
			isShowSchool: true,
			checkOne: function(item){
				$("#add").attr("disabled",false); 
				$scope.campusidSelected = item.id;
				$scope.searchArguments = {
        			campusid:item.id,
				}
				$scope.createPageable();
			}
		};

		//add
		$scope.add = {};
		$scope.add.startTime = new Date();
		$scope.add.endTime = new Date();
		$scope.AddSys = function(){
			//$scope.add.startTime = $filter('date')($scope.add.startTime, 'yyyy-MM-dd');
			$scope.add.startTime = $scope.add.startTime.getTime();
			$scope.add.endTime = $scope.add.endTime.getTime();
			Semester.Add($scope.campusidSelected ,$scope.add, function(){
				$("#addModal").modal('hide');
				location.reload();
			})
		}

		//update
		$scope.edit = {};
		$scope.edit.startTime = new Date();
		$scope.edit.endTime = new Date();
		$scope.updateSys = function(item){
			$scope.message = "";
			var temp = angular.copy(item);
			$scope.edit.id = temp.id;
			$scope.edit.semester = temp.semester;
			$scope.edit.description = temp.description;
			$scope.edit.startTime = new Date(temp.startTime);
			$scope.edit.endTime = new Date(temp.endTime);
		}

		$scope.EditSys = function(){
			$scope.message = "";
			var item = $scope.edit;
			$scope.message = "";

			$scope.edit.startTime = $scope.edit.startTime.getTime();
			$scope.edit.endTime = $scope.edit.endTime.getTime();
			$scope.edit.campusId = $scope.campusidSelected;
			Semester.Update($scope.edit, function(){
				$("#editModal").modal('hide');
				location.reload();
			})
		}

		//delete
		$scope.deleteSys = function(item){
			$rootScope.confirm_modal = {};
				$rootScope.confirm_modal.title="提示";
				$rootScope.confirm_modal.content="确定删除吗？";
				$rootScope.confirm_modal.click = function(){
					Semester.Delete(item.id, function(){
						location.reload();
					})
				}
				$('#confirm-modal').modal();
		}


		$scope.createPageable = function (){
			$scope.searchPageable = new Pageable();

			$scope.searchPageable.size = 6;
			$scope.searchPageable.page = 0;

			$scope.searchPageable.pageNumbers.startPage = 1;
        	$scope.searchPageable.pageNumbers.content.length = 8;
        	$scope.searchPageable.arguments=$scope.searchArguments;
        	// Set the placeholder elements
        	$scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

        	// Build the pageable object
        	$scope.searchPageable.build(GetSemester);
        	$scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
		}
	}]);