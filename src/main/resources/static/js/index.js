// Toggle sidebar
function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    const mainContent = document.getElementById('mainContent');
    const toggleBtn = document.getElementById('sidebarToggle');
    sidebar.classList.toggle('sidebar-hidden');
    mainContent.classList.toggle('main-content-expanded');
    toggleBtn.classList.toggle('sidebar-toggle-hidden');
    toggleBtn.querySelector('i').classList.toggle('fa-chevron-left');
    toggleBtn.querySelector('i').classList.toggle('fa-chevron-right');
}

// Tab management
const tabList = document.getElementById('tabList');
const mainIframe = document.getElementById('mainIframe');

function openTab(element) {
    const title = element.textContent;
    const url = element.getAttribute('data-href');

    // Check if tab already exists
    const existingTab = Array.from(tabList.children).find(tab => tab.querySelector('span').textContent === title);
    if (existingTab) {
        setActiveTab(existingTab);
        mainIframe.src = url;
        return;
    }

    // Create new tab
    const newTab = document.createElement('li');
    newTab.className = 'tab-item';
    newTab.innerHTML = `<span data-href="${url}" onclick="openTab(this)">${title}</span><em onclick="closeTab(this)">×</em>`;
    tabList.appendChild(newTab);
    setActiveTab(newTab);
    mainIframe.src = url;
}

function setActiveTab(tab) {
    document.querySelectorAll('.tab-item').forEach(t => t.classList.remove('active'));
    tab.classList.add('active');
}

function closeTab(element) {
    const tab = element.parentElement;
    const isActive = tab.classList.contains('active');
    const prevTab = tab.previousElementSibling;
    tab.remove();

    if (isActive && prevTab) {
        setActiveTab(prevTab);
        mainIframe.src = prevTab.querySelector('span').getAttribute('data-href');
    } else if (isActive) {
        mainIframe.src = '/welcome';
        const firstTab = tabList.firstElementChild;
        if (firstTab) setActiveTab(firstTab);
    }
}

// Tab scrolling
function scrollTabs(direction) {
    const scrollAmount = 100;
    if (direction === 'left') {
        tabList.scrollLeft -= scrollAmount;
    } else {
        tabList.scrollLeft += scrollAmount;
    }
}

// Open links in sidebar as tabs
document.querySelectorAll('.nav-list a').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();
        const url = this.getAttribute('href');
        const title = this.getAttribute('data-title');
        const tabElement = document.createElement('span');
        tabElement.textContent = title;
        tabElement.setAttribute('data-href', url);
        openTab(tabElement);
    });
});