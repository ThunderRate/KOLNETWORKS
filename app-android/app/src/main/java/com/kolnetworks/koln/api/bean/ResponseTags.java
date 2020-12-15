package com.kolnetworks.koln.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ResponseTags {
    /**
     * success : true
     * tags : [{"tag_id":13,"name":"16-20","slug":"age1","category":2},{"tag_id":14,"name":"21-30","slug":"age2","category":2},{"tag_id":15,"name":"31-40","slug":"age3","category":2},{"tag_id":16,"name":"41-50","slug":"age4","category":2},{"tag_id":17,"name":"51-60","slug":"age5","category":2},{"tag_id":18,"name":"61-70","slug":"age6","category":2},{"tag_id":19,"name":"71up","slug":"age7","category":2}]
     */

    private boolean success;
    private List<TagsBean> tags;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean implements Parcelable {
        /**
         * tag_id : 13
         * name : 16-20
         * slug : age1
         * category : 2
         */

        private int tag_id;
        private String name;
        private String slug;
        private int category;

        public int getTag_id() {
            return tag_id;
        }

        public void setTag_id(int tag_id) {
            this.tag_id = tag_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.tag_id);
            dest.writeString(this.name);
            dest.writeString(this.slug);
            dest.writeInt(this.category);
        }

        public TagsBean() {
        }

        protected TagsBean(Parcel in) {
            this.tag_id = in.readInt();
            this.name = in.readString();
            this.slug = in.readString();
            this.category = in.readInt();
        }

        public static final Parcelable.Creator<TagsBean> CREATOR = new Parcelable.Creator<TagsBean>() {
            @Override
            public TagsBean createFromParcel(Parcel source) {
                return new TagsBean(source);
            }

            @Override
            public TagsBean[] newArray(int size) {
                return new TagsBean[size];
            }
        };
    }
}
