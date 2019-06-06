app.controller("searchController",function ($scope,$controller,searchService) {
    $controller('publicController',{$scope:$scope});//继承

    $scope.searchItem=function () {
        searchService.searchItem($scope.searchMap).success(function (response) {
            $scope.resultMap = response;
        });
    }

});