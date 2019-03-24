'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
const follow = require('./follow'); // function to hop multiple links by "rel"

var root = "/api";

// tag::app[]
class App extends React.Component {

    constructor(props) {
        super(props);
        this.onCreate = this.onCreate.bind(this);
        this.state = {users: [], attributes: User.getRequiredAttributes()};
    }

    componentDidMount() {
        client({method: 'GET', path: root + '/user', registry: "" }).done(response => {
            this.setState({users: response.entity.content});
        });
    }

    onCreate(newUser) {
        client({
            method: 'POST',
            path: root + '/user',
            entity: newUser,
            headers: {'Content-Type': 'application/json'}
        });

    }


    render() {
        return (
            <div>
                <CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
                <br />
                <UserList users={this.state.users}/>
            </div>

        )
    }

}

// end::app[]

// tag::user-list[]
class UserList extends React.Component {
    render() {

        console.log(this);
        console.log(this.props);

        const users = this.props.users.map(user =>
            <User key={user.links[1].href} user={user.user}/>);

        return (
            <div>
                <table>
                    <tbody>
                    <tr>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Email</th>
                    </tr>
                    {users}
                    </tbody>
                </table>
            </div>
        )

    }
}

// end::user-list[]

// tag::users[]
class User extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.user.username}</td>
                <td>{this.props.user.password}</td>
                <td>{this.props.user.email}</td>
            </tr>
        )

    }

    static getRequiredAttributes() {
        return ['username', 'password', 'email']
    }
}

class CreateDialog extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        const newUser = {};
        this.props.attributes.forEach(attribute => {
            newUser[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
        });
        this.props.onCreate(newUser);


        // clear out the dialog's inputs
        this.props.attributes.forEach(attribute => {
            ReactDOM.findDOMNode(this.refs[attribute]).value = '';
        });

        // Navigate away from the dialog to hide it.
        window.location = "#";
    }


    render() {

        const inputs = this.props.attributes.map(attribute =>
            <p key={attribute}>
                <input type="text" placeholder={attribute} ref={attribute} className="field"/>
            </p>
        );

        return (
            <div>
                <div id="createUser" className="modalDialog">
                    <div>
                        <h2>Create new user </h2>

                        <form>
                            {inputs}
                            <br/>
                            <button onClick={this.handleSubmit}>Create</button>
                        </form>
                    </div>
                </div>
            </div>
        )
    }

}

// end::users[]

// tag::render[]
ReactDOM.render(
    <App/>,
    document.getElementById('react')
);
// end::render[]