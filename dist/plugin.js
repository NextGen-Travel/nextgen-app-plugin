var capacitorNextgenAppPlugin = (function (exports, core) {
    'use strict';

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

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
