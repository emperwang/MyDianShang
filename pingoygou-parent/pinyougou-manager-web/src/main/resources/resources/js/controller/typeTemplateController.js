 //控制层 
app.controller('typeTemplateController' ,function($scope,$controller,typeTemplateService,brandService,specificationService){
	
	$controller('publicController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		typeTemplateService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		typeTemplateService.findPage(page,rows).success(
			function(response){
				$scope.list=response.results;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		typeTemplateService.findOne(id).success(
			function(response){
				$scope.entity= response;
				//把从数据库获取的数据转换为json对象
                $scope.entity.brandIds=JSON.parse($scope.entity.brandIds);
                $scope.entity.specIds=JSON.parse($scope.entity.specIds);
                $scope.entity.customAttributeItems=JSON.parse($scope.entity.customAttributeItems);
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=typeTemplateService.update( $scope.entity ); //修改  
		}else{
			serviceObject=typeTemplateService.add( $scope.entity  );//增加 
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
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		typeTemplateService.dele( $scope.selectIds ).success(
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
		typeTemplateService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.results;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//$scope.brandList={data:[{id:1,text:'联想'},{id:2,text:'华为'},{id:3,text:'小米'}]}
    $scope.brandList={data:[]}
	//品牌列表
    $scope.findOptionBrandList=function () {
        brandService.findOptionList().success(function (datas) {
            $scope.brandList={data:datas};
        });
    }
    //规则列表
    $scope.specList={data:[]}
    $scope.findOptionSpecList=function () {
        specificationService.selectOptionList().success(function (datas) {
            $scope.specList={data:datas};
        });
    }
    //扩展属性行添加
    //$scope.entity.customAttributeItems=[];
	$scope.addTableRow=function () {
        $scope.entity.customAttributeItems.push({});
    }
    //扩展属性行删除
    $scope.deleTableRow=function (index) {
        $scope.entity.customAttributeItems.splice(index,1);
    }
    
});
