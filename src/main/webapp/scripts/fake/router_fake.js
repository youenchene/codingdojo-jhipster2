'use strict';

simplecraApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/fake', {
                    templateUrl: 'views/fakes.html',
                    controller: 'FakeController',
                    resolve:{
                        resolvedFake: ['Fake', function (Fake) {
                            return Fake.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
