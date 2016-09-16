(function() {
    'use strict';
    angular
        .module('quotesApp')
        .factory('Quote', Quote);

    Quote.$inject = ['$resource', 'DateUtils'];

    function Quote ($resource, DateUtils) {
        var resourceUrl =  'api/quotes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lasttradetime = DateUtils.convertDateTimeFromServer(data.lasttradetime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
