package com.namviet.vtvtravel.response.f2filter;

import com.namviet.vtvtravel.model.filter.ItemTab;
import com.namviet.vtvtravel.response.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class FilterByPageResponse extends BaseResponse implements Serializable {
    private String codeSet;

    public String getCodeSet() {
        return codeSet;
    }

    public void setCodeSet(String codeSet) {
        this.codeSet = codeSet;
    }

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data implements Serializable {
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        private String id;
        private String label;
        private String name;
        private String code;
        private String select;
        private String field;
        private List<Input> inputs;

        public String getSelect() {
            return select;
        }

        public String getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public List<Input> getInputs() {
            return inputs;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setSelect(String select) {
            this.select = select;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public void setInputs(List<Input> inputs) {
            this.inputs = inputs;
        }

        public class Input implements Serializable{
            private boolean isSelected;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            private String value;
            private String label;

            public String getValue() {
                return value;
            }

            public String getLabel() {
                return label;
            }
        }
    }
}
