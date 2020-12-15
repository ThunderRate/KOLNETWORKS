package com.kolnetworks.koln.api.bean;

import java.util.List;

public class ResponseBoards {

    /**
     * success : true
     * board : {"board_id":33,"uuid":"e6f73b10-85d2-11ea-9d1a-fd06d10b9040","content":"集合啦～動物森林朋友2","created_at":1587695145,"comments":[{"content":"再增資","created_at":1587868551,"updated_at":null,"is_bg":false,"user":{"uuid":"d1babc10-8508-11ea-9c05-d72015886950","display_name":"tonyfdsaqq","photo":"https://scontent-nrt1-1.cdninstagram.com/v/t51.2885-19/10624422_1681113512113145_1928453536_a.jpg?_nc_ht=scontent-nrt1-1.cdninstagram.com&_nc_ohc=GL3xneNNhScAX8QWePV&oh=836b843904b68aff3b27eae6ea1bca86&oe=5ECE4A2C"}}]}
     */

    private boolean success;
    private BoardBean board;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    public static class BoardBean {
        /**
         * board_id : 33
         * uuid : e6f73b10-85d2-11ea-9d1a-fd06d10b9040
         * content : 集合啦～動物森林朋友2
         * created_at : 1587695145
         * comments : [{"content":"再增資","created_at":1587868551,"updated_at":null,"is_bg":false,"user":{"uuid":"d1babc10-8508-11ea-9c05-d72015886950","display_name":"tonyfdsaqq","photo":"https://scontent-nrt1-1.cdninstagram.com/v/t51.2885-19/10624422_1681113512113145_1928453536_a.jpg?_nc_ht=scontent-nrt1-1.cdninstagram.com&_nc_ohc=GL3xneNNhScAX8QWePV&oh=836b843904b68aff3b27eae6ea1bca86&oe=5ECE4A2C"}}]
         */

        private int board_id;
        private String uuid;
        private String content;
        private int created_at;
        private List<CommentsBean> comments;

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean {
            /**
             * content : 再增資
             * created_at : 1587868551
             * updated_at : null
             * is_bg : false
             * user : {"uuid":"d1babc10-8508-11ea-9c05-d72015886950","display_name":"tonyfdsaqq","photo":"https://scontent-nrt1-1.cdninstagram.com/v/t51.2885-19/10624422_1681113512113145_1928453536_a.jpg?_nc_ht=scontent-nrt1-1.cdninstagram.com&_nc_ohc=GL3xneNNhScAX8QWePV&oh=836b843904b68aff3b27eae6ea1bca86&oe=5ECE4A2C"}
             */

            private String content;
            private int created_at;
            private Object updated_at;
            private boolean is_bg;
            private UserBean user;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public Object getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(Object updated_at) {
                this.updated_at = updated_at;
            }

            public boolean isIs_bg() {
                return is_bg;
            }

            public void setIs_bg(boolean is_bg) {
                this.is_bg = is_bg;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class UserBean {
                /**
                 * uuid : d1babc10-8508-11ea-9c05-d72015886950
                 * display_name : tonyfdsaqq
                 * photo : https://scontent-nrt1-1.cdninstagram.com/v/t51.2885-19/10624422_1681113512113145_1928453536_a.jpg?_nc_ht=scontent-nrt1-1.cdninstagram.com&_nc_ohc=GL3xneNNhScAX8QWePV&oh=836b843904b68aff3b27eae6ea1bca86&oe=5ECE4A2C
                 */

                private String uuid;
                private String display_name;
                private String photo;

                public String getUuid() {
                    return uuid;
                }

                public void setUuid(String uuid) {
                    this.uuid = uuid;
                }

                public String getDisplay_name() {
                    return display_name;
                }

                public void setDisplay_name(String display_name) {
                    this.display_name = display_name;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }
            }
        }
    }
}
