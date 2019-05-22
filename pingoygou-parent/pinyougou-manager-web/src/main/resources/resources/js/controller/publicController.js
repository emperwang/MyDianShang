//此文件记录一些共性的函数
app.controller('publicController',function ($scope) {

//记录页面的信息
/*currentPage:当前页 totalItem:总记录数 itemsPerPage 每页显示几个,
 perPageOptions 设置每页能显示多少个
 * onChange 改变时触发的事件 */
$scope.paginationConf={
    currentPage: 1,
    totalItems: 10,
    itemsPerPage: 10,
    perPageOptions: [10,20,30,40,50],
    onChange: function(){
        $scope.reloadList();
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
    //刷新页面
    $scope.reloadList=function(){
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    }

    //把字符串提取出来想要的key的值
    $scope.jsonToKeyString=function (jsonString,key) {
        if (jsonString.indexOf("\"")==1 && jsonString.lastIndexOf("\"")==jsonString.length){
            jsonString = jsonString.substring(1,jsonString.length-1);
        }
        var json = JSON.parse(jsonString);
        var value="";
        for(var i=0 ;i < json.length;i++){
            value += json[i][key] + ",";
        }
        value = value.substring(0,value.length-1);

        return value;
    }
});