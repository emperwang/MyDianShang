//brand的控制层实现
app.controller("brandController",function ($scope,$http,brandService) {
    $scope.findall=function(){
        brandService.findall().success(function(data){
            $scope.brandlist=data;
        });
    }
    //记录页面的信息
    /**
     * currentPage 当前页
     * totalItem  总记录数
     * itemsPerPage 每页显示几个
     * perPageOptions 设置每页能显示多少个
     * onChange 改变时触发的事件
     */
    $scope.paginationConf={
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10,20,30,40,50],
        onChange: function(){
            $scope.reload();
        }
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
    $scope.entity={}   //存储brand的变量，用于添加和修改
    $scope.save=function(){
        if($scope.entity.id != null){
            $scope.update();
        }else{
            $scope.add();
        }
    }
    //用户选择的复选框
    $scope.selectIds=[]
    $scope.updateSelectIds=function ($event,id) {  //$event表示源target
        if ($event.target.checked) {
            $scope.selectIds.push(id);
        }else{
            var index = $scope.selectIds.indexOf(id);   //获取元素在数组中的位置
            $scope.selectIds.splice(index,1); //参数1：要删除的元素的位置  参数2：删除的个数
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