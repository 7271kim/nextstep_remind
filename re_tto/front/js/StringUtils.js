class StringUtils {
    static defaultIfBlank(string, defaultStr) {
        //- defaultIfBlank( input string값, 디폴트 값 ) : input이 null일 시 원하는 디폴트 값 세팅
        let result = string;

        if (typeof string === 'number') {
            result = parseInt(string);
        } else if (typeof string === 'undefined' || string === null) {
            result = defaultStr;
        }

        return result;
    }
}

export { StringUtils };