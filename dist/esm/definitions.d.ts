export interface NextgenAppPluginPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    /** universalLink 是 ios 登入必要的參數 */
    wxInit(params: {
        appId: string;
        universalLink?: string;
    }): Promise<any>;
    wxLogin(): Promise<{
        code: string;
    }>;
}
