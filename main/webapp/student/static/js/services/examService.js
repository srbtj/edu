var examServices = angular.module('examServices', ['ngResource', "nourConfig"]);


examServices.factory('VerifyExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(studentId, bookId, callback){
			$http.get(config.HOST + 'exam/verifypaper/'+studentId + '/' + bookId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.getAllInfo = function(userId, callback){
			$http.get(config.HOST + 'verifyexams/' + userId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.submitExam = function(data, callback){
			$http.post(config.HOST + 'exam/verifypaper', data)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,error){
				console.log(data);
			});
		};
		return api;
	}]);


examServices.factory('WordExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(studentId, bookId, callback){
			$http.get(config.HOST + 'exam/wordpaper/'+studentId + '/' + bookId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.getAllInfo = function(userId ,callback){
			$http.get(config.HOST + 'wordexams/' + userId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.submitExam = function(data, callback){
			$http.post(config.HOST + 'exam/wordpaper', data)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,error){
				console.log(data);
			});
		};
		return api;
	}]);


examServices.factory('ThinkExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(bookId, callback){
			$http.get(config.HOST + 'exam/thinkpaper/'+ bookId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.getAllInfo = function(userId ,callback){
			$http.get(config.HOST + 'thinkexams/' + userId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.submitExam = function(data, callback){
			$http.post(config.HOST + 'exam/thinkpaper', data)
			.success(function(data, status, headers, config){
				callback(data);
			})
			.error(function(data,error){
				console.log(data);
			});
		};
		api.getOne = function(userid, questionid, page, size, callback){
			$http.get(config.HOST + 'subjectiveanswers/' + userid + '/' + questionid + '?page=' + page + '&size=' + size + '&sortBy=id')
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.isReviewed = function(bookid, userid, callback, errorCallback){
			$http.get(config.HOST + 'books/' + bookid + '/students/' + userid + '/isreviewed')
			.success(function(data){
				callback();
			})
			.error(function(data){
				errorCallback();
			});
		}
		return api;
	}]);


examServices.factory('CapacityExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(levelId, callback){
			$http.get(config.HOST + 'exam/wordpaper/'+levelId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.getAllInfo = function(userId ,callback){
			$http.get(config.HOST + 'capacityexams/' + userId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);

examServices.factory('PassExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(userid, callback){
			$http.get(config.HOST + 'verifyexams/pass/'+userid)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);


