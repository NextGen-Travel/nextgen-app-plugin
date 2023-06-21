export interface NextgenAppPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  wxInit(params: { appId: string }): Promise<any>
  wxLogin(): Promise<any>;
}
