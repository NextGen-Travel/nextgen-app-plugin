import { WebPlugin } from '@capacitor/core';

import type { NextgenAppPluginPlugin } from './definitions';

export class NextgenAppPluginWeb
  extends WebPlugin
  implements NextgenAppPluginPlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
