package kcn.kea;

import kcn.kea.controller.HomeController;
import kcn.kea.controller.SearchController;
import kcn.kea.model.DataMap;
import kcn.kea.repository.ITrackRepository;
import kcn.kea.repository.TrackListRepository;
import kcn.kea.server.ControllerNameServer;
import kcn.kea.server.ViewNameServer;
import kcn.kea.service.GET;
import kcn.kea.presentation.Console;
import kcn.kea.presentation.IPresent;
import kcn.kea.view.*;
import kcn.kea.view.search.SearchResultsView;

public class Main
{

    public static void main(String[] args)
    {
        IPresent console = new Console(System.out);
        GET.setStream(console.getStream());

        ITrackRepository trackRepository = new TrackListRepository(new DummyData().generateDummyTracks());

        registerViews();
        initializeControllers(trackRepository);

        DataMap map = new DataMap();

        ViewNameServer.viewMap().get("playView").show(map);
        map = ViewNameServer.viewMap().get("failure").show(map);
        map = ControllerNameServer.call("showMainMenu").run(map);

    }

    private static void registerViews()
    {
        BaseConsoleView failureView = new FailureView();

        BaseConsoleView mainMenuView = new MainMenuView();
        BaseConsoleView searchMainView = new SearchMainView();
        BaseConsoleView searchResultsView = new SearchResultsView();
        BaseConsoleView playView = new PlayView();

        ViewNameServer.viewMap().put(failureView.getViewName(), failureView);
        ViewNameServer.viewMap().put(searchMainView.getViewName(), searchMainView);
        ViewNameServer.viewMap().put(searchResultsView.getViewName(), searchResultsView);
        ViewNameServer.viewMap().put(mainMenuView.getViewName(), mainMenuView);
        ViewNameServer.viewMap().put(playView.getViewName(),playView);
    }

    /**
     * If the controller are written right, the controller registers itself with the ControllerNameServer
     * through its constructor
     */
    private static void initializeControllers(ITrackRepository trackRepository)
    {
        ControllerNameServer.ctrlMap().putAll(new HomeController(trackRepository).controlHooks());
        ControllerNameServer.ctrlMap().putAll(new SearchController(trackRepository).controlHooks());
    }
}
