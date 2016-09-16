(function () {
    'use strict';

    angular
        .module('quotesApp')

        /*
         Languages codes are ISO_639-1 codes, see http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
         They are written in English to avoid character encoding issues (not a perfect solution)
         */
        .constant('LANGUAGES', [
            'en',
            'zh-cn',
            'cs',
            'nl',
            'fr',
            'de',
            'el',
            'hi',
            'it',
            'ja',
            'ko',
            'pl',
            'pt-br',
            'ro',
            'ru',
            'es',
            'sv',
            'tr'
            // jhipster-needle-i18n-language-constant - JHipster will add/remove languages in this array
        ]
    );
})();
