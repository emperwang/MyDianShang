app.controller("searchController",function ($scope,$controller,searchService) {
    $controller('publicController',{$scope:$scope});//继承
    $scope.searchMap={'keywords':'','category':'','brand':'','spec':{}};
    $scope.searchItem=function () {
        searchService.searchItem($scope.searchMap).success(function (response) {
            $scope.resultMap = response;
        });
    }
    $scope.addSearchItem=function (key,value) {
        if (key == 'category' || key == 'brand'){  // 说明用户点击的是品牌和分类
            $scope.searchMap[key] = value;
        }else{ // 否则是规格选项
            $scope.searchMap.spec[key]=value;
        }
    }
});