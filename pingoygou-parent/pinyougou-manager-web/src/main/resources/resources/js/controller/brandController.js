//brand的控制层实现

app.controller("brandController",function ($scope,$controller,$http,brandService) {
    //控制器的继承
    $controller('publicController',{$scope:$scope});

    $scope.findall=function(){
        brandService.findall().success(function(data){
            $scope.brandlist=data;
        });
    }

    $scope.searchEntity={}  //查询参数  放到前面是为了reload函数执行时使用
    //刷新页面
    $scope.reload=function(){
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    }


    //请求分页的数据
    $scope.findPage=function(pagenum,size){
        brandService.findPage(pagenum,size).success(function(data){
            $scope.brandlist=data.results;
            $scope.paginationConf.totalItems=data.total;
        });
    }

    //存储brand的变量，用于添加和修改
    $scope.entity={}
    $scope.save=function(){
        if($scope.entity.id != null){
            $scope.update();
        }else{
            $scope.add();
        }
    }

    //删除
    $scope.deletes=function () {
        brandService.deletes($scope.selectIds).success(function (data) {
            if(data.success){
                $scope.reload();
            }else{
                alert(data.message);
            }
        });
    }
    //查询
    $scope.search=function (pagenum,size) {
        brandService.search(pagenum,size,$scope.searchEntity).success(function (data) {
            $scope.brandlist=data.results;
            $scope.paginationConf.totalItems=data.total;
        });
    }
    //添加
    $scope.add=function () {
        brandService.add($scope.entity).success(function (data) {
            if(data.success){
                $scope.reload();
            }else{
                alert(data.message);
            }
        });
    }
    //修改
    $scope.update=function () {
        brandService.update($scope.entity).success(function (data) {
            if(data.success){
                $scope.reload();
            }else{
                alert(data.message);
            }
        });
    }
    //通过id查找
    $scope.findOne=function (id) {
        brandService.findOne(id).success(function (data) {
            $scope.entity=data;
        });
    }
});