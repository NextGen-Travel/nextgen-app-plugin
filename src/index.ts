import { registerPlugin } from '@capacitor/core';

import type { NextgenAppPluginPlugin } from './definitions';

const NextgenAppPlugin = registerPlugin<NextgenAppPluginPlugin>(
  'NextgenAppPlugin',
  {
    web: () => import('./web').then(m => new m.NextgenAppPluginWeb()),
  },
);

export * from './definitions';
export { NextgenAppPlugin };
