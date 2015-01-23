'use strict';

simplecraApp.factory('Fake', function ($resource) {
        return $resource('app/rest/fakes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
