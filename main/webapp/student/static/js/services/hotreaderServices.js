var hotreaderServices = angular.module('hotreaderServices', ['ngResource', "nourConfig"]);

/*
    hotreader object(s)
*/
hotreaderServices.factory('Hotreader', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/hotreaders",
            {by:'@by', id:'@id'}, {}
        );
}]);
