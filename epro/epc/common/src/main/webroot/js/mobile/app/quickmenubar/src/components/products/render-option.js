import React from 'react';
import {TEXT_SOLD_OUT, BTN_SELECT_DEFAULT} from "../../i18n/i18n-ko";

const RenderOption = (props) => {
    if(props.options.length === 0){
        return null;
    }
    return (
        <div className="select required">
            <select required={true}
                    onChange={props.onChange}>
                <option value=""
                        selected={props.selectedOptionValue === '' ? true : false}>
                    {BTN_SELECT_DEFAULT}
                </option>
                {props.options.map((option) =>
                    <option value={option.value}
                            selected={props.selectedOptionValue === option.value ? true : false}
                            disabled={option.isSoldOut}>
                        {option.name}{option.isSoldOut?' - ' + TEXT_SOLD_OUT:''}
                    </option>
                )}
            </select>
        </div>
    )
};

export default RenderOption;