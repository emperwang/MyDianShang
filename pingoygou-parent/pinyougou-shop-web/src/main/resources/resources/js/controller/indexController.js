app.controller("indexController",function ($scope,$controller,loginService) {
    $controller('publicController',{$scope:$scope});//继承
    $scope.showLoginName=function () {
        loginService.loginName().success(function (data) {
           $scope.loginName=data.loginName;
        });
    }
});