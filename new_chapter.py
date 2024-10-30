import os

base_dir = "./src/main/kotlin"
src_chapter = "ch08"
dst_chapter = "ch09"


for paths in os.walk(f"{base_dir}/{src_chapter}"):
    parent = paths[0]
    for child in paths[2]:
        dst_dir = parent.replace(src_chapter, dst_chapter)
        if not os.path.exists(dst_dir):
            os.makedirs(dst_dir)
        dst = os.path.join(dst_dir, child)
        src = os.path.join(parent, child)
        with open(src, "r", encoding="utf-8") as src_file:
            with open(dst, "w", encoding="utf-8") as dst_file:
                dst_file.write(src_file.read().replace(src_chapter, dst_chapter))