//readingCenterTestFailedController.js

ctrls.controller("readingCenterTestFailedController", ['$scope', '$rootScope', function ($scope, $rootScope) {
    $scope.name = $rootScope.exam.bookName;
    $scope.reExam = function(){
    	window.location.href="/student/protype/index.html#/readingCenter/multipleTesting";
    }
}]);