import { registerPlugin } from '@capacitor/core';
const NextgenAppPlugin = registerPlugin('NextgenAppPlugin', {
    web: () => import('./web').then(m => new m.NextgenAppPluginWeb()),
});
export * from './definitions';
export { NextgenAppPlugin };
//# sourceMappingURL=index.js.map