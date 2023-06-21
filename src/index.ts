import { registerPlugin } from '@capacitor/core';

import type { NextgenAppPluginPlugin } from './definitions';

const NextgenAppPlugin = registerPlugin<NextgenAppPluginPlugin>(
  'NextgenAppPlugin',
  {},
);

export * from './definitions';
export { NextgenAppPlugin };
