//readingCenterAddBookAdvancedSearchCtrl.jsc
ctrls.controller("readingCenterAddBookAdvancedSearchController", 
    ['$scope','$rootScope','$stateParams','Pageable','ConditionSearch','QuickSearch','Book','config', 'BookOperation', 'Dropzone',
    function ($scope,$rootScope,$stateParams,Pageable,ConditionSearch,QuickSearch,Book, config, BookOperation, Dropzone) {


//    $scope.searchContent="";
$scope.imageServer = config.IMAGESERVER;
$scope.selectBook = {};
var pageSize = 4;
var searchTerm='isbn';

$scope.searchArguments = {
    level:0,
    category:0,
    testType:0,
    literature:0,
    grade:0,
    category:0,
    language:0,
    resource:0,
    pointRange:0,
    searchTerm:""
}

//    $scope.searchContent = searchContent;
$scope.statuses_grade = [{
    id: 0,
    name:"全部年级",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 1,
    name: "1年级",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 2,
    name: "2年级",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 3,
    name: "3年级",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 4,
    name: "4年级",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 5,
    name: "5年级",
    callback: function(){$scope.search($scope.searchArguments)}
}];

$scope.statuses_category = [{
    id: 0,
    name:"全部类型",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 1,
    name: "类型一",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 2,
    name: "类型二",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 3,
    name: "类型三",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 4,
    name: "类型四",
    callback: function(){$scope.search($scope.searchArguments)}
}, {
    id: 5,
    name: "类型五",
    callback: function(){$scope.search($scope.searchArguments)}
}];
$scope.selected_status = 0;

$scope.createPageable = function (){
    $scope.searchPageable = new Pageable();

    $scope.searchPageable.size = 4;
    $scope.searchPageable.page = 1;

    $scope.searchPageable.arguments=$scope.searchArguments;
        // Set the startPage and length of number page array
        //console.log($scope.searchArguments);
        
        $scope.searchPageable.pageNumbers.startPage = 1;
        $scope.searchPageable.pageNumbers.content.length = 8;
        // Set the placeholder elements
        $scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

        // Build the pageable object
        $scope.searchPageable.build(ConditionSearch);
        
        $scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
        //console.log($scope.searchPageable);
    }

    $scope.createPageable();

    $scope.searchByName=function(searchContent){
        //console.log(searchContent);
        $scope.createPageable();

    };

    $scope.search = function(searchArguments){
        $scope.createPageable();

    };

    $scope.Update = function(terms){
         var temp = angular.copy(terms);
         $scope.selectBookOfId = temp.id;
         $scope.selectBook.isbn = temp.isbn;
         $scope.selectBook.pictureUrl = temp.pictureUrl;
         $scope.selectBook.name = temp.name;
         $scope.selectBook.description = temp.description;
         $scope.selectBook.author = temp.author;
         $scope.selectBook.publisher = temp.publisher;
         $scope.selectBook.publicationDate = new Date(temp.publicationDate);
         $scope.selectBook.pageCount = temp.pageCount;
         $scope.selectBook.wordCount = temp.wordCount;
         $scope.selectBook.point = temp.point;
         $scope.selectBook.coin = temp.coin;
         $scope.selectBook.price = temp.price;
         $scope.selectBook.highPrice = temp.highPrice;
         $scope.selectBook.binding = temp.binding;
         $scope.selectBook.authorIntroduction = temp.authorIntroduction;
         $scope.selectBook.evaluationNum = temp.evaluationNum;
         $scope.selectBook.extra = {};
         $scope.selectBook.extra.level = temp.extra.level;
         $scope.selectBook.extra.language = temp.extra.language;
         $scope.selectBook.extra.literature = temp.extra.literature;
         $scope.selectBook.extra.testType = 0;
         $scope.selectBook.extra.grade = temp.extra.grade;
         $scope.selectBook.extra.category = temp.extra.category;
         $scope.selectBook.extra.resource = 0;
         $scope.selectBook.extra.pointRange = temp.extra.pointRange;
         $scope.selectBook.extra.ageRange = temp.extra.ageRange;
        $("#updateBook").modal({backdrop: 'static', keyboard: false});
    };
    $scope.dropzone1 = Dropzone("image-edit", config.USERICON, function(url){
        $scope.selectBook.pictureUrl = url;
    });

    $scope.submitForm = function(isValid, isDirty){
        if (!isValid) {
            return;
        };
        if (!isDirty) {
            $("#updateBook").modal("hide");
            return;
        };
        $scope.selectBook.publicationDate = $scope.selectBook.publicationDate.getTime();
        BookOperation.updateBook($scope.selectBookOfId, $scope.selectBook, function(){
            location.reload();
        })
    }

    $scope.DeleteBook = function(terms){
        $rootScope.confirm_modal = {
            title: "提示",
            content: "确定删除吗",
            click: function(){
                BookOperation.deleteBook(terms.id, function(){
                    location.reload();
                })
            }
        };
        $('#confirm-modal').modal('show');
    }

    $scope.add = {};
    $scope.add.description = "";
    $scope.add.authorIntroduction = "";
    $scope.add.binding = "hardback";
    $scope.add.pictureUrl = "";
    $scope.add.evaluationNum = 0;
    $scope.add.extra = {};
    $scope.add.extra.level = 0;
    $scope.add.extra.ageRange = 0;
    $scope.add.extra.language = 0;
    $scope.add.extra.literature = 0;
    $scope.add.extra.testType = 0;
    $scope.add.extra.grade = 0;
    $scope.add.extra.category = 0;
    $scope.add.extra.resource = 0;
    $scope.add.extra.pointRange = 0;
    $scope.add.publicationDate = new Date();
    $scope.dropzone = Dropzone("image-add", config.USERICON, function(url){
        $scope.add.pictureUrl = url;
    });

    // Update remove file callback
    $scope.dropzone.on('removedfile', function(){
    });
    $scope.AddSys = function(){
        $scope.add.publicationDate = $scope.add.publicationDate.getTime();
        BookOperation.Add($scope.add, function(data) {
            $rootScope.modal = {
            title: "提示",
            content: "添加成功",
            click: function(){
                location.reload();
            }
        };
        $('#alert-modal').modal('show');
        });
            
        }

    if($stateParams.searchTerm!== ""){
     $scope.searchByName($stateParams.searchTerm);
     $scope.searchArguments.searchTerm = $stateParams.searchTerm;
 }

}]);

ctrls.filter('formatPictrueUrl', function () {
    return function (data) {
        var url = data.slice(27);
        if (url.search('/') === 0) return data;
        return url;
    };
});
ctrls.filter('fsize_format', function () {
    return function (data) {
        if (data.length > 50) {
            return data.substring(0,50);
        };
        return data;
    };
});