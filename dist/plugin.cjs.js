'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const NextgenAppPlugin = core.registerPlugin('NextgenAppPlugin', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.NextgenAppPluginWeb()),
});

class NextgenAppPluginWeb extends core.WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    NextgenAppPluginWeb: NextgenAppPluginWeb
});

exports.NextgenAppPlugin = NextgenAppPlugin;
//# sourceMappingURL=plugin.cjs.js.map
