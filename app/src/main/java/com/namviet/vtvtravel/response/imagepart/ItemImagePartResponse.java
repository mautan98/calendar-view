package com.namviet.vtvtravel.response.imagepart;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class ItemImagePartResponse extends BaseResponse {
    private boolean isLoadMore;

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private String name;
        private String limit;
        private String page;
        private String hasMore;
        private String more_link;
        private List<ItemImagePartResponse.Data.Item> items;

        public String getName() {
            return name;
        }

        public String getLimit() {
            return limit;
        }

        public String getPage() {
            return page;
        }

        public String getHasMore() {
            return hasMore;
        }

        public String getMore_link() {
            return more_link;
        }

        public List<ItemImagePartResponse.Data.Item> getItems() {
            return items;
        }

        public class Item {
            private String id;
            private String content_type;
            private String type;
            private String name;
            private String title;
            private String description;
            private String author;
            private String view_count;
            private String content;
            private String short_description;
            private String logo_url;
            private List<String> thumb_url;
            private String created;
            private String count_like;
            private String comment_count;
            private String link_share;
            private boolean isLiked;
            private String likeCount;
            private String category_tree_code;
            private String category_tree_name;

            private boolean isHideTextShowMore;

            public boolean isHideTextShowMore() {
                return isHideTextShowMore;
            }

            public void setHideTextShowMore(boolean isHideTextShowMore) {
                isHideTextShowMore = isHideTextShowMore;
            }

            public String getCategory_tree_code() {
                return category_tree_code;
            }

            public String getCategory_tree_name() {
                return category_tree_name;
            }

            public boolean isLiked() {
                return isLiked;
            }

            public void setLiked(boolean liked) {
                isLiked = liked;
            }

            public String getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(String likeCount) {
                this.likeCount = likeCount;
            }

            public String getId() {
                return id;
            }

            public String getContent_type() {
                return content_type;
            }

            public String getType() {
                return type;
            }

            public String getName() {
                return name;
            }

            public String getTitle() {
                return title;
            }

            public String getDescription() {
                return description;
            }

            public String getAuthor() {
                return author;
            }

            public String getView_count() {
                return view_count;
            }

            public String getContent() {
                return content;
            }

            public String getShort_description() {
                return short_description;
            }

            public String getLogo_url() {
                return logo_url;
            }

            public List<String> getThumb_url() {
                return thumb_url;
            }

            public String getCreated() {
                return created;
            }

            public String getCount_like() {
                return count_like;
            }

            public String getCount_comment() {
                return comment_count;
            }

            public String getLink_share() {
                return link_share;
            }
        }
    }
}

