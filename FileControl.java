1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79
80
81
82
83
84
85
86
87
88
89
90
91
92
93
94
95
96
97
98
99
100
101
102
103
104
105
106
107
108
109
110
111
112
113
114
115
116
117
118
119
120
121
122
123
124
125
126
127
128
129
130
131
132
133
134
135
136
137
138
139
140
141
142
143
144
145
146
147
148
149
150
151
152
153
154
155
156
157
158
159
160
161
162
163
164
165
166
167
168
169
170
171
172
173
174
175
176
177
178
179
180
181
182
183
184
185
186
187
188
189
190
191
192
193
194
195
196
197
198
199
200
201
202
203
204
205
206
207
208
209
210
211
212
213
214
215
216
217
218
219
220
221
222
223
224
225
226
227
228
229
230
231
232
233
234
235
236
237
238
239
240
241
242
243
244
245
246
247
248
249
250
251
252
253
254
255
public class SDCardHelper {
 
     // 判断SD卡是否被挂载
     public static boolean isSDCardMounted() {
         // return Environment.getExternalStorageState().equals("mounted");
         return Environment.getExternalStorageState().equals(
         Environment.MEDIA_MOUNTED);
    }
 
    // 获取SD卡的根目录
    public static String getSDCardBaseDir() {
         if (isSDCardMounted()) {
               return Environment.getExternalStorageDirectory().getAbsolutePath();
         }
         return null;
    }
 
    // 获取SD卡的完整空间大小，返回MB
    public static long getSDCardSize() {
         if (isSDCardMounted()) {
              StatFs fs = new StatFs(getSDCardBaseDir());
              long count = fs.getBlockCountLong();
              long size = fs.getBlockSizeLong();
              return count * size / 1024 / 1024;
         }
         return 0;
    }
 
    // 获取SD卡的剩余空间大小
    public static long getSDCardFreeSize() {
         if (isSDCardMounted()) {
               StatFs fs = new StatFs(getSDCardBaseDir());
               long count = fs.getFreeBlocksLong();
               long size = fs.getBlockSizeLong();
               return count * size / 1024 / 1024;
         }
         return 0;
    }
 
    // 获取SD卡的可用空间大小
    public static long getSDCardAvailableSize() {
         if (isSDCardMounted()) {
               StatFs fs = new StatFs(getSDCardBaseDir());
               long count = fs.getAvailableBlocksLong();
               long size = fs.getBlockSizeLong();
               return count * size / 1024 / 1024;
         }
         return 0;
    }
 
    // 往SD卡的公有目录下保存文件
    public static boolean saveFileToSDCardPublicDir(byte[] data, String type, String fileName) {
         BufferedOutputStream bos = null;
         if (isSDCardMounted()) {
               File file = Environment.getExternalStoragePublicDirectory(type);
               try {
                    bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                    bos.write(data);
                    bos.flush();
                    return true;
               } catch (Exception e) {
                    e.printStackTrace();
               } finally {
                    try {
                          bos.close();
                    } catch (IOException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                    }
               }
          }
          return false;
     }
 
     // 往SD卡的自定义目录下保存文件
     public static boolean saveFileToSDCardCustomDir(byte[] data, String dir, String fileName) {
          BufferedOutputStream bos = null;
          if (isSDCardMounted()) {
                File file = new File(getSDCardBaseDir() + File.separator + dir);
                if (!file.exists()) {
                      file.mkdirs();// 递归创建自定义目录
                }
                try {
                      bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                      bos.write(data);
                      bos.flush();
                      return true;
                } catch (Exception e) {
                      e.printStackTrace();
                } finally {
                      try {
                            bos.close();
                      } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                      }
                }
           }
           return false;
     }
 
     // 往SD卡的私有Files目录下保存文件
     public static boolean saveFileToSDCardPrivateFilesDir(byte[] data, String type, String fileName, Context context) {
         BufferedOutputStream bos = null;
         if (isSDCardMounted()) {
               File file = context.getExternalFilesDir(type);
               try {
                      bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                      bos.write(data);
                      bos.flush();
                      return true;
               } catch (Exception e) {
                      e.printStackTrace();
               } finally {
                      try {
                            bos.close();
                      } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                      }
               }
          }
          return false;
     }
 
     // 往SD卡的私有Cache目录下保存文件
     public static boolean saveFileToSDCardPrivateCacheDir(byte[] data, String fileName, Context context) {
          BufferedOutputStream bos = null;
          if (isSDCardMounted()) {
                File file = context.getExternalCacheDir();
                try {
                      bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                      bos.write(data);
                      bos.flush();
                      return true;
                } catch (Exception e) {
                      e.printStackTrace();
                } finally {
                      try {
                            bos.close();
                      } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                      }
               }
          }
          return false;
     }
 
     // 保存bitmap图片到SDCard的私有Cache目录
     public static boolean saveBitmapToSDCardPrivateCacheDir(Bitmap bitmap, String fileName, Context context) {
          if (isSDCardMounted()) {
                BufferedOutputStream bos = null;
                // 获取私有的Cache缓存目录
                File file = context.getExternalCacheDir();
 
                try {
                       bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                       if (fileName != null && (fileName.contains(".png") || fileName.contains(".PNG"))) {
                              bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                       } else {
                              bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                       }
                       bos.flush();
                } catch (Exception e) {
                       e.printStackTrace();
                } finally {
                       if (bos != null) {
                            try {
                                 bos.close();
                            } catch (IOException e) {
                                 e.printStackTrace();
                            }
                       }
                 }
                 return true;
          } else {
                return false;
          }
     }
 
     // 从SD卡获取文件
     public static byte[] loadFileFromSDCard(String fileDir) {
          BufferedInputStream bis = null;
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
 
          try {
                bis = new BufferedInputStream(new FileInputStream(new File(fileDir)));
                byte[] buffer = new byte[8 * 1024];
                int c = 0;
                while ((c = bis.read(buffer)) != -1) {
                     baos.write(buffer, 0, c);
                     baos.flush();
                }
                return baos.toByteArray();
          } catch (Exception e) {
                e.printStackTrace();
          } finally {
                try {
                     baos.close();
                     bis.close();
                } catch (IOException e) {
                     e.printStackTrace();
                }
          }
          return null;
     }
 
     // 从SDCard中寻找指定目录下的文件，返回Bitmap
     public Bitmap loadBitmapFromSDCard(String filePath) {
          byte[] data = loadFileFromSDCard(filePath);
          if (data != null) {
               Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
               if (bm != null) {
                     return bm;
               }
          }
          return null;
     }
 
     // 获取SD卡公有目录的路径
     public static String getSDCardPublicDir(String type) {
          return Environment.getExternalStoragePublicDirectory(type).toString();
     }
 
     // 获取SD卡私有Cache目录的路径
     public static String getSDCardPrivateCacheDir(Context context) {
          return context.getExternalCacheDir().getAbsolutePath();
     }
 
     // 获取SD卡私有Files目录的路径
     public static String getSDCardPrivateFilesDir(Context context, String type) {
          return context.getExternalFilesDir(type).getAbsolutePath();
     }
 
     public static boolean isFileExist(String filePath) {
          File file = new File(filePath);
          return file.isFile();
     }
 
     // 从sdcard中删除文件
     public static boolean removeFileFromSDCard(String filePath) {
          File file = new File(filePath);
          if (file.exists()) {
               try {
                     file.delete();
                     return true;
               } catch (Exception e) {
                     return false;
               }
          } else {
               return false;
          }
     }
}