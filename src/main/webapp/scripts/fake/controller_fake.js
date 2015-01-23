'use strict';

simplecraApp.controller('FakeController', function ($scope, resolvedFake, Fake) {

        $scope.fakes = resolvedFake;

        $scope.create = function () {
            Fake.save($scope.fake,
                function () {
                    $scope.fakes = Fake.query();
                    $('#saveFakeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.fake = Fake.get({id: id});
            $('#saveFakeModal').modal('show');
        };

        $scope.delete = function (id) {
            Fake.delete({id: id},
                function () {
                    $scope.fakes = Fake.query();
                });
        };

        $scope.clear = function () {
            $scope.fake = {name: null, id: null};
        };
    });
