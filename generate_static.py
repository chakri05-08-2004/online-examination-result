import os
import shutil
import re

templates_dir = "src/main/resources/templates"
static_dir = "src/main/resources/static"
output_dir = "public"

# Create or clear output directory
if os.path.exists(output_dir):
    shutil.rmtree(output_dir)
os.makedirs(output_dir)
os.makedirs(os.path.join(output_dir, "css"))
os.makedirs(os.path.join(output_dir, "js"))

# Copy static files
shutil.copytree(os.path.join(static_dir, "css"), os.path.join(output_dir, "css"), dirs_exist_ok=True)
if os.path.exists(os.path.join(static_dir, "js")):
    shutil.copytree(os.path.join(static_dir, "js"), os.path.join(output_dir, "js"), dirs_exist_ok=True)

# Read layout
with open(os.path.join(templates_dir, "layout.html"), "r", encoding="utf-8") as f:
    layout_content = f.read()

# Replace thymeleaf hrefs with static hrefs
layout_content = layout_content.replace('th:href="@{/css/style.css}"', 'href="css/style.css"')
layout_content = layout_content.replace('th:src="@{/js/main.js}"', 'src="js/main.js"')

# Quick mappings for links
layout_content = layout_content.replace('th:href="@{/}"', 'href="index.html"')
layout_content = layout_content.replace('th:href="@{/jobs}"', 'href="job-list.html"')
layout_content = layout_content.replace('th:href="@{/login}"', 'href="login.html"')
layout_content = layout_content.replace('th:href="@{/register}"', 'href="register.html"')

pages = ["index.html", "job-list.html", "login.html", "register.html", "student-dashboard.html"]

for page in pages:
    page_path = os.path.join(templates_dir, page)
    if not os.path.exists(page_path):
        continue
    
    with open(page_path, "r", encoding="utf-8") as f:
        page_content = f.read()
    
    # Extract just the contents inside <div th:fragment="content">
    match = re.search(r'<div th:fragment="content">(.*)</div>\s*</body>', page_content, re.DOTALL)
    if match:
        inner_html = match.group(1)
    else:
        # Fallback if the previous regex doesn't exactly match
        match = re.search(r'<div th:fragment="content">(.*)</div>\s*</div>\s*</body>', page_content, re.DOTALL)
        if match:
            inner_html = match.group(1)
        else:
            inner_html = page_content # Fallback

    # Merge into layout
    final_html = layout_content.replace('<div th:replace="${content}"></div>', inner_html)
    
    # Write to public
    with open(os.path.join(output_dir, page), "w", encoding="utf-8") as f:
        f.write(final_html)

print("Static site generated in public/")
