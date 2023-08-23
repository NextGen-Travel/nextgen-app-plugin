# nextgen-app-plugin

Nextgen plugin

## Install

```bash
npm install nextgen-app-plugin
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`wxInit(...)`](#wxinit)
* [`wxLogin()`](#wxlogin)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### wxInit(...)

```typescript
wxInit(params: { appId: string; }) => Promise<any>
```

| Param        | Type                            |
| ------------ | ------------------------------- |
| **`params`** | <code>{ appId: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### wxLogin()

```typescript
wxLogin() => Promise<{ code: string; }>
```

**Returns:** <code>Promise&lt;{ code: string; }&gt;</code>

--------------------

</docgen-api>
