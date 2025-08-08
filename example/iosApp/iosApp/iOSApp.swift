import SwiftUI


@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            MainView()
        }
    }
}

struct MainView: View {

    var body: some View {
        VStack {
            NavigationView {
                VStack (alignment: .leading) {
                    Text("github.com/lucasferreiramachado/kapp-deeplink")
                        .font(.caption2)
                        .multilineTextAlignment(.leading)
                        .padding(.horizontal, 16)
                    List {
                        NavigationLink(
                            destination: ComposeAppExampleView()
                                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                        ) {
                            Text("Compose App Example")
                        }
                        NavigationLink(
                            destination: ModuleExampleView()
                        ) {
                            Text("KappDeeplink Module Example")
                        }
                    }.navigationTitle("Example")
                }.frame(maxWidth: .infinity)
            }
        }
    }
}


struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
