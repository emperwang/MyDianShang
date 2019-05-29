 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location ,goodsService,itemCatService,typeTemplateService){
	
	$controller('publicController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(){
		//url:http://127.0.0.1:9101/admin/goods-edit.do#?id=149187842867932
		var id = $location.search()['id'];  //通过页面传参
		if(id == null){
			return;
		}
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;
				//商品修改的富文本框回显
                editor.html(response.goodsDesc.introduction);
                //商品扩展属性显示
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse(response.goodsDesc.customAttributeItems);
                //规格列表转换
                $scope.entity.goodsDesc.specificationItems = JSON.parse(response.goodsDesc.specificationItems);
                //转换itemList中的spec对json对象
				for(var i=0;i<response.itemList.length;i++){
                    response.itemList[i].spec=JSON.parse(response.itemList[i].spec);
				}
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
    //添加
    $scope.add=function(){
        $scope.entity.goodsDesc.introduction=editor.html();
    	goodsService.add( $scope.entity  ).success(
            function(response){
                if(response.success){
                    //添加成功后需要把输入框清空
                    $scope.entity={}
                    editor.html("");   //富文本框清空
                }else{
                    alert(response.message);
                }
            }
        );
    }
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.results;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    $scope.entity={ goodsDesc:{itemImages:[],specificationItems:[]}  };
	//第一级下拉列表
	$scope.findFirstItemCatList=function () {
        itemCatService.findByParentId(0).success(function (data) {
			$scope.firstItemCatlist1=data;
        })
    }

    //二级下拉列表
	//这里使用了angluarjs的监视功能,如果category1Id的值发生变化，那么后面的函数就会执行
	$scope.$watch('entity.goods.category1Id',function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(function (data) {
            $scope.firstItemCatlist2=data;
        })
    });

	//三级下拉列表
    $scope.$watch('entity.goods.category2Id',function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(function (data) {
            $scope.firstItemCatlist3=data;
        })
    });
    //模板ID
    $scope.$watch('entity.goods.category3Id',function (newValue,oldValue) {
        itemCatService.findOne(newValue).success(function (data) {
            $scope.entity.goods.typeTemplateId=data.itemCat.typeId;
        });
    });

    //品牌下拉表
    $scope.$watch('entity.goods.typeTemplateId',function (newValue,oldValue) {
        typeTemplateService.findOne(newValue).success(function (data) {
        	$scope.brandList=data.brandIds;
            $scope.brandList=JSON.parse($scope.brandList);
            //扩展属性
			if ($location.search()['id']==null) { // 如果id未null,那么确定为添加,否则为修改
                $scope.entity.goodsDesc.customAttributeItems = data.customAttributeItems;
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.entity.goodsDesc.customAttributeItems);
            }
        });
        //查询规格的后台数据
        typeTemplateService.findSpecList(newValue).success(function(response){
			$scope.specificationList=response;
		});
    });
	//把用户选择的规格添加到entity中，然后提交到goodsDesc表
	$scope.updateSpecificationItem=function ($event,name,keyValue) {
		var object = $scope.searchObjectInList($scope.entity.goodsDesc.specificationItems,'attributeName',name);
        //已经存在
		if(object != null){
            if($event.target.checked) {
                object.attributeValue.push(keyValue);
            }else{
                object.attributeValue.splice(object.attributeValue.indexOf(keyValue),1);
                //如果此项的长度为0，那么就把此项全部删除
                if(object.attributeValue.length==0){
                    $scope.entity.goodsDesc.specificationItems.splice(
                        $scope.entity.goodsDesc.specificationItems.indexOf(object),1);
				}
            }
		}else {//第一次添加 ,attributeValue定义为list
            $scope.entity.goodsDesc.specificationItems.push({'attributeName':name,'attributeValue':[keyValue]})
        }
    }
    //动态添加规格的列表  SKU列表
	$scope.createItemList=function () {
        $scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0'} ];//列表初始化
		var items = $scope.entity.goodsDesc.specificationItems;
		for(var i=0;i<items.length;i++){
			$scope.entity.itemList = addColumn($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
		}
	}
    addColumn=function (list, column, columnValue) {
        var newList=[]
        for(var i=0;i<list.length;i++){
            var oldRow = list[i];
            for(var j=0;j<columnValue.length;j++){
                var newRow = JSON.parse(JSON.stringify(oldRow));  //深拷贝
                newRow.spec[column]=columnValue[j];
                newList.push(newRow);
            }
        }
        return newList;
    }

    //商品审核状态
	$scope.status=['未审核','已审核','审核未通过','已关闭'];

	//保存分类的名称
	$scope.itemCatList=[];
	//查找商品分类
	$scope.findItemCatList=function () {
        itemCatService.findAll().success(function (response) {
			for(var i=0;i<response.length;i++){
				//以id为list索引,name为索引的值
                $scope.itemCatList[response[i].id]=response[i].name;
			}
        });
    }
	//判断规格列表是否应该勾选
	$scope.judgeAttribute=function (specName,optionName) {
        var specItems = $scope.entity.goodsDesc.specificationItems;
		var specList = $scope.searchObjectInList(specItems,'attributeName',specName);
		if (specList != null){
			if(specList.attributeValue.indexOf(optionName)>=0){
				return true;
			}else{
				return false;//没找到,那一定为没有,返回false
			}
		}else {
			return false;//连规格选项都没有,那么一定为false
		}
    }
});	
