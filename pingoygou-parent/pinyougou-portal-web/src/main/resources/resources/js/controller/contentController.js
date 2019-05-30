app.controller("contentController",function ($scope,$controller,contentService) {

    //控制器的继承
    $controller('publicController',{$scope:$scope});

    //轮播广告的存放地方
    $scope.contentList=[]
    $scope.findByCategoryId=function (categoryId) {
        contentService.findByCategoryId(categoryId).success(function (response) {
            $scope.contentList[categoryId]=response;
        });
    }
});