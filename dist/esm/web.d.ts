import { WebPlugin } from '@capacitor/core';
import type { NextgenAppPluginPlugin } from './definitions';
export declare class NextgenAppPluginWeb extends WebPlugin implements NextgenAppPluginPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
