package ki.fanxuebo.server;

import ki.fanxuebo.server.pojo.Context;
import ki.fanxuebo.server.pojo.Host;
import ki.fanxuebo.server.pojo.Mapper;
import ki.fanxuebo.server.pojo.Wrapper;
import ki.fanxuebo.server.servlet.HttpServlet;
import ki.fanxuebo.server.thread.RequestThread;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-16 18:23:25 星期四
 */
public class BootStrap {

    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        try {
            bootStrap.init();
            bootStrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(mapper.getPort());
        System.out.println("Minicat start on port: " + mapper.getPort());

        //多线程使用线程池改造
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                50,
                100L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        while (true) {
            Socket socket = serverSocket.accept();
            // 使用线程池多线程改造
            threadPoolExecutor.execute(new RequestThread(socket, mapper));
        }

    }

    private Mapper mapper = new Mapper();

    private void init() {
        // 加载server.xml文件
        loadServer();
    }

    private void loadServer() {
        System.out.println("加载server.xml文件，获取要监听的端口号，主机地址，项目路径");
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            //<Server>
            Element rootElement = document.getRootElement();
            //<Connector port="8080"/>
            List<Element> connectorElementList = rootElement.selectNodes("//Connector");
            String port = connectorElementList.get(0).attributeValue("port");
            mapper.setPort(Integer.valueOf(port));
            //<Host name="localhost"  appBase="webapps"/>
            List<Element> hostElementList = rootElement.selectNodes("//Host");
            for (int i = 0; i < hostElementList.size(); i++) {
                Element hostElement = hostElementList.get(i);
                String ip = hostElement.attributeValue("name");
                String appBase = hostElement.attributeValue("appBase");
                Host host = new Host();
                host.setIp(ip);
                mapper.getHostList().add(host);

                File file = new File(appBase);
                File[] files = file.listFiles();
                for (int j = 0; j < files.length; j++) {
                    File webFile = files[j];
                    String webName = webFile.getName();
                    Context context = new Context();
                    context.setWebName(webName);

                    File[] files1 = webFile.listFiles();
                    for (int k = 0; k < files1.length; k++) {
                        File demoFile = files1[k];
                        if ("web.xml".equals(demoFile.getName())) {
                            loadWrapper(demoFile.getAbsolutePath(), context);
                            host.getContextList().add(context);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadWrapper(String absolutePath, Context context) {
        try {
            InputStream resourceAsStream = new FileInputStream(new File(absolutePath));
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();

            List<Element> servletElementList = rootElement.selectNodes("//servlet");
            for (int i = 0; i < servletElementList.size(); i++) {
                Element servletElement = servletElementList.get(i);
                //<servlet-name>myServlet</servlet-name>
                String servletName = servletElement.selectSingleNode("servlet-name").getStringValue();
                //<servlet-class>ki.fanxuebo.server.servlet.MyServlet</servlet-class>
                String servletClass = servletElement.selectSingleNode("servlet-class").getStringValue();

                // 根据servlet-name的值找到url-pattern
                Element servletMappingElement = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                //<url-pattern>/ki</url-pattern>
                String urlPattern = servletMappingElement.selectSingleNode("url-pattern").getStringValue();
                Wrapper wrapper = new Wrapper();
                wrapper.setUrl(urlPattern);
                wrapper.setServlet((HttpServlet) Class.forName(servletClass).newInstance());
                context.getWrapperList().add(wrapper);
            }
        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
