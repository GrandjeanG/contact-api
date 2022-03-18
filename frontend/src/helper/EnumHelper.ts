export const enumHelper = {

    /**
     * Return array of enum
     */
    // eslint-disable-next-line @typescript-eslint/ban-types
    'enumKeys': <O extends object, K extends keyof O = keyof O>(obj: O): K[] => {
        return Object.keys(obj).filter(k => Number.isNaN(+k)) as K[];
    }
}