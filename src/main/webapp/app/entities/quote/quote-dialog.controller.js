(function() {
    'use strict';

    angular
        .module('quotesApp')
        .controller('QuoteDialogController', QuoteDialogController);

    QuoteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Quote'];

    function QuoteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Quote) {
        var vm = this;

        vm.quote = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.quote.id !== null) {
                Quote.update(vm.quote, onSaveSuccess, onSaveError);
            } else {
                Quote.save(vm.quote, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('quotesApp:quoteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.lasttradetime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
