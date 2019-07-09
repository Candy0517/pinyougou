app.controller('baseController', function ($scope) {
//分页参数
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();
        }
    };

//刷新
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    //复选框
    $scope.selectIds = [];
    $scope.updateSelection = function ($event, id) {
        if ($event.target.checked) {
            $scope.selectIds.push(id);
        } else {
            var index = $event.target.indexOf(id);
            $scope.selectIds.splice(index, 1);
        }
    };

    //解析Json字符串
    $scope.jsonToString = function (jsonString, key) {

        var jsonList = JSON.parse(jsonString);
        var value = "";

        for (var i = 0; i < jsonList.length; i++) {
            if (i >0) {
                value += " , ";
            }
            value += jsonList[i][key];
        }

        return value;
    };



});