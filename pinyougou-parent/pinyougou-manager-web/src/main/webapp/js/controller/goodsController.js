 //控制层 
app.controller('goodsController' ,function($scope,$controller,itemCatService,goodsService,typeTemplateService,$location){
	
	$controller('baseController',{$scope:$scope});//继承
	
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
		/*goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);*/

		var id= $location.search()['id'];//获取参数值
		if(id==null){
			return ;
		}
		goodsService.findOne(id).success(
			function (response) {
				$scope.entity = response;
				editor.html($scope.entity.goodsDesc.introduction);//回显富文本框
				$scope.entity.goodsDesc.itemImages=JSON.parse($scope.entity.goodsDesc.itemImages);//回显图片
				$scope.entity.goodsDesc.customAttributeItems=JSON.parse( $scope.entity.goodsDesc.customAttributeItems);//扩展属性
				$scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);//规格型号

				for(var i=0; i< $scope.entity.itemList.length;i++){
					$scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec);
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
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					alert(response.message);
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数


				for (var i = 0; i < $scope.list.length; i++) {
					if ($scope.list[i].auditStatus == '0') {
						$scope.list[i].auditStatus = '未申请';
					} else if ($scope.list[i].auditStatus == '1') {
						$scope.list[i].auditStatus = '申请中';
					} else if ($scope.list[i].auditStatus == '2') {
						$scope.list[i].auditStatus = '审核通过';
					} else if ($scope.list[i].auditStatus == '3') {
						$scope.list[i].auditStatus = '已驳回';
					}
				}
			}			
		);
	};
	//分类回显名字
	$scope.findItemCatList = function () {
		$scope.ItemCatList = [];
		itemCatService.findAll().success(function (response) {

			for (var i = 0; i < response.length; i++) {
				$scope.ItemCatList[response[i].id] = response[i].name;
			}

		})


	};

	//根据规格名称和选项名称返回是否被勾选
	$scope.checkAttributeValue=function (specName,optionName) {

		var items = $scope.entity.goodsDesc.specificationItems;
		var object = $scope.searchObjectByKey(items,'attributeName',specName);
		if (object == null || object.attributeValue.indexOf(optionName)<0){
			return false;
		} else {
			return true;
		}

	};

	$scope.entity = {goodsDesc: {itemImages: [], specificationItems: []}};

	//一级下拉菜单
	$scope.select1List = function () {
		itemCatService.findByParentId(0).success(function (response) {
			$scope.item1List = response;
		})
	};

	//二级下拉菜单
	$scope.$watch("entity.goods.category1Id", function (newValue, oldValue) {
		itemCatService.findByParentId(newValue).success(function (response) {
			$scope.item2List = response;
			/*$scope.item3List = null;
            $scope.entity.goods.typeTemplateId = null;
            $scope.typeTemplate.brandIds = null;
            $scope.entity.goodsDesc.customAttributeItems = null;*/
		})
	});


	//三级下拉菜单
	$scope.$watch("entity.goods.category2Id", function (newValue, oldValue) {

		itemCatService.findByParentId(newValue).success(function (response) {
			$scope.item3List = response;
		})
	});
	// typeid
	$scope.$watch("entity.goods.category3Id", function (newValue, oldValue) {
		itemCatService.findOne(newValue).success(function (response) {
			$scope.entity.goods.typeTemplateId = response.typeId;
		})
	});

	//品牌,属性
	$scope.$watch("entity.goods.typeTemplateId", function (newValue, oldValue) {

		typeTemplateService.findOne(newValue).success(function (response) {
			$scope.typeTemplate = response;//获取模板
			$scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);//获取品牌字段里的集合
			if ($location.search()['id']==null){//如果地址栏没有ID
				$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
			}


		});

		typeTemplateService.findSpecList(newValue).success(function (response) {
			$scope.specList = response;
		});
	});

	$scope.updateSpecAttribute = function ($event, name, value) {

		var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems, 'attributeName', name);

		if (object != null) {
			if ($event.target.checked) {
				object.attributeValue.push(value);
			} else {
				var index = object.attributeValue.indexOf(object);
				object.attributeValue.splice(index, 1);
				if (object.attributeValue.length == 0) {
					var indexOf = $scope.entity.goodsDesc.specificationItems.indexOf(object);
					$scope.entity.goodsDesc.specificationItems.splice(indexOf, 1);
				}
			}

		} else {
			$scope.entity.goodsDesc.specificationItems.push({"attributeName": name, "attributeValue": [value]})
		}

	};

	$scope.updateStatus=function (status) {
		goodsService.updateStatus($scope.selectIds,status).success(function (response) {
			if (response.success){
				alert(response.message);
				$scope.reloadList();//刷新列表
				$scope.selectIds=[];//清空ID集合
			} else {
				alert(response.message);
			}
		})
	}
});	
