ctrls.controller("readingCenterBookDetailsController", ['$scope', '$rootScope', '$stateParams', 'para','WeeklyHotSearch','OneBookInShelf'
    ,'BookDetail' , 'Student', 'NoteView', 'NoteTake', 'config', 'AddReview', function($rootScope,$scope, $stateParams, para,WeeklyHotSearch,OneBookInShelf,BookDetail, Student, NoteView, NoteTake, config, AddReview){
	$scope.name = '书籍详情';


    var bookDetail = BookDetail.get({ id: $stateParams.bookId }, function(){
        console.log(bookDetail);

        $scope.student = Student.get({id: $rootScope.id}, function(){
            // Initlizate the note entity
            $scope.noteTake = new NoteTake(bookDetail);

            // Make an instance of the NoteView
            $scope.noteView = new NoteView();
            $scope.arguments = {by: "books", id: bookDetail.id, sortBy: "commentCount" };

            // Transmit arguments to note search engine
            $scope.noteView.ShowMoreNotes($scope.arguments);
        });

        $scope.wordTest = function(){
            $rootScope.exam.id = 1;
            $rootScope.exam.returnURL = "/protype/index.html#/readingCenter/bookDetails/" + bookDetail.id + "/";
            $rootScope.exam.bookId = bookDetail.id;
            $rootScope.exam.bookName = bookDetail.name;
            $rootScope.exam.typeName = "我的书架 > 词汇训练";
        }

        $scope.thinkTest = function(){
            $rootScope.exam.id = 2;
            $rootScope.exam.returnURL = "/student/protype/index.html#/readingCenter/bookDetails/" + bookDetail.id + "/";
            $rootScope.exam.bookId = bookDetail.id;
            $rootScope.exam.bookName = bookDetail.name;
            $rootScope.exam.typeName = "我的书架 > 思维训练";
        }

        /* Goto or open the action defined $stateParams.action
           example:
                /bookDetails/1/takenote
                /bookDetails/1/intro
                /bookDetails/1/catalog
                /bookDetails/1/review
                /bookDetails/1/note
                /bookDetails/1/recommand
                /bookDetails/1/multimedia
        */
        $(function() {
            switch($stateParams.action){
                case "takenote":
                    $('#takenote').click();
                    break;
                default:
                    if ($stateParams.action !== undefined){
                        $('ul.nav a[href="#' + $stateParams.action + '"]').tab('show');
                    }
           }
        });


        $scope.isInShelf = false;

        var thisBookinshelf = OneBookInShelf.get({id:$rootScope.id,bookId:$stateParams.bookId},function(){
            console.log(thisBookinshelf);
            var verify;
            if(thisBookinshelf.readState){
                verify = true;
            }
            else {
                verify = false;
            }
            $scope.verify = verify;
            $scope.verifyExam = function(){
                $rootScope.exam.id = 0;
                $rootScope.exam.returnURL = "/student/protype/index.html#/readingCenter/bookDetails/" + bookDetail.id + "/";
                $rootScope.exam.bookId = bookDetail.id;
                $rootScope.exam.bookName = bookDetail.name;
                $rootScope.exam.typeName = "我的书架 > 认证训练";
            }
            $scope.isInShelf = true;
        },function(){
          $scope.isInShelf = false;
        });

        // $scope.isInShelf = function(){
        //       var isInShelf ;
        //       OneBookInShelf.get({id:$rootScope.id,bookId:$stateParams.bookId},function(){
        //         isInShelf = true;
        //       });
        //       console.log(isInShelf);
        //       if(typeof(isInShelf) !== 'undefined')
        //         return true;
        //       else {
        //         return false;
        //       }
        //     };

        //get review
        $scope.review = {};
        $scope.review.page = 0;
        $scope.review.size = 3;
        $scope.review.state = { more : "加载更多",loading: "更多加载中...",nomore: "没有了"};
        $scope.review.currentStete = $scope.review.state.loading;
        getReviewData();
        $scope.showMoreReview = function(){
            $scope.review.page = $scope.review.page + 1;
            getReviewData();
        }

        // Get the image server
        $scope.imageServer = config.IMAGESERVER;
    })

    $scope.bookDetails = bookDetail;


    $scope.hots=WeeklyHotSearch.get({page:0,size:5,level:0,testType:0,literature:0,category:0
                                        ,grade:0,language:0,resource:0,pointRange:0},function(){
        });
    $scope.imageServer = config.IMAGESERVER;

    function getReviewData(){
        if ($scope.review.currentStete == $scope.review.state.nomore) {
            return;
        };
        AddReview.getAllById(bookDetail.id, $scope.review.page, $scope.review.size, function(dataReview){
            if ($scope.review.source == undefined) {
                $scope.review.source = dataReview.content;
            }
            else{
                $scope.review.source.push(dataReview.content);
            }
            $scope.review.currentStete = $scope.review.state.more;
            if ($scope.review.source.length >=dataReview.totalElements) {
                $scope.review.currentStete = $scope.review.state.nomore;
            }
        });
    }

    $scope.showLightBox = function(url){
        $scope.showLightBox.url = $scope.imageServer + url;
    }
}]);
